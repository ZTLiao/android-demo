
import frida

def on_message(message, data):
    if message['type'] == 'send':
        print('[*] {0}'.format(message['payload']))
    else:
        print(message)

device = frida.get_usb_device()

if __name__ == '__main__':
    try:
        session = device.attach('com.kanxue.test')
        print('[Info] Attach success!')
        with open('rpc.js') as f:
            jscode = f.read()
        script = session.create_script(jscode)
        script.on('message', on_message)
        script.load()
        script.exports.logv('i am from python logv')
        script.exports.loge('i am from python loge')
        with open('', 'r') as classListFile:
            content = classListFile.read()
            classListFile.close()
        classListArray = content.split('\n')
        for i in classListArray:
            i = i[i: len(i) - 1]
            i = i.replace('/', '.')
            className = i
            print(className)
            if className.startswith('anet.', 0, len(className)):
                pass
            else:
                print('start deal with -> ' + className)
                script.exports.loadClass(className)
        session.detach()
    except Exception as e:
        print('[Info] Spawn and attach failed!')
        print(e)