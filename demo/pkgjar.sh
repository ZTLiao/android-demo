#!/usr/bin/env bash

/Users/liaozetao/Library/Android/sdk/build-tools/30.0.3/dx --dex --output=./ ./

java -jar apktool_2.6.1.jar d test1.jar

