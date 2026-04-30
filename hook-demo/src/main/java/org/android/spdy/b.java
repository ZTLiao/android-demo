package org.android.spdy;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertPathValidator;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

public class b {
    private Set c;
    private PKIXParameters d;
    private CertPathValidator e;
    private CertificateFactory f;

    public static boolean a;
    private static String[] b0;
    private static Object g;
    private static b h;

    static {
        String[] stringArray = new String[]{"-----BEGIN CERTIFICATE-----\nMIIDdTCCAl2gAwIBAgILBAAAAAABFUtaw5QwDQYJKoZIhvcNAQEFBQAwVzELMAkG\nA1UEBhMCQkUxGTAXBgNVBAoTEEdsb2JhbFNpZ24gbnYtc2ExEDAOBgNVBAsTB1Jv\nb3QgQ0ExGzAZBgNVBAMTEkdsb2JhbFNpZ24gUm9vdCBDQTAeFw05ODA5MDExMjAw\nMDBaFw0yODAxMjgxMjAwMDBaMFcxCzAJBgNVBAYTAkJFMRkwFwYDVQQKExBHbG9i\nYWxTaWduIG52LXNhMRAwDgYDVQQLEwdSb290IENBMRswGQYDVQQDExJHbG9iYWxT\naWduIFJvb3QgQ0EwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDaDuaZ\njc6j40+Kfvvxi4Mla+pIH/EqsLmVEQS98GPR4mdmzxzdzxtIK+6NiY6arymAZavp\nxy0Sy6scTHAHoT0KMM0VjU/43dSMUBUc71DuxC73/OlS8pF94G3VNTCOXkNz8kHp\n1Wrjsok6Vjk4bwY8iGlbKk3Fp1S4bInMm/k8yuX9ifUSPJJ4ltbcdG6TRGHRjcdG\nsnUOhugZitVtbNV4FpWi6cgKOOvyJBNPc1STE4U6G7weNLWLBYy5d4ux2x8gkasJ\nU26Qzns3dLlwR5EiUWMWea6xrkEmCMgZK9FGqkjWZCrXgzT/LCrBbBlDSgeF59N8\n9iFo7+ryUp9/k5DPAgMBAAGjQjBAMA4GA1UdDwEB/wQEAwIBBjAPBgNVHRMBAf8E\nBTADAQH/MB0GA1UdDgQWBBRge2YaRQ2XyolQL30EzTSo//z9SzANBgkqhkiG9w0B\nAQUFAAOCAQEA1nPnfE920I2/7LqivjTFKDK1fPxsnCwrvQmeU79rXqoRSLblCKOz\nyj1hTdNGCbM+w6DjY1Ub8rrvrTnhQ7k4o+YviiY776BQVvnGCv04zcQLcFGUl5gE\n38NflNUVyRRBnMRddWQVDf9VMOyGj/8N7yy5Y0b2qvzfvGn9LhJIZJrglfCm7ymP\nAbEVtQwdpf5pLGkkeB6zpxxxYu7KyJesF12KwvhHhm4qxFYxldBniYUr+WymXUad\nDKqC5JlR3XC321Y9YeRq4VzW9v493kHMB65jUr9TU/Qr6cf9tveCX4XSQRjbgbME\nHMUfpIBvFSDJ3gyICh3WZlXi/EjJKSZp4A==\n-----END CERTIFICATE-----\n","-----BEGIN CERTIFICATE-----\nMIIDXzCCAkegAwIBAgILBAAAAAABIVhTCKIwDQYJKoZIhvcNAQELBQAwTDEgMB4G\nA1UECxMXR2xvYmFsU2lnbiBSb290IENBIC0gUjMxEzARBgNVBAoTCkdsb2JhbFNp\nZ24xEzARBgNVBAMTCkdsb2JhbFNpZ24wHhcNMDkwMzE4MTAwMDAwWhcNMjkwMzE4\nMTAwMDAwWjBMMSAwHgYDVQQLExdHbG9iYWxTaWduIFJvb3QgQ0EgLSBSMzETMBEG\nA1UEChMKR2xvYmFsU2lnbjETMBEGA1UEAxMKR2xvYmFsU2lnbjCCASIwDQYJKoZI\nhvcNAQEBBQADggEPADCCAQoCggEBAMwldpB5BngiFvXAg7aEyiie/QV2EcWtiHL8\nRgJDx7KKnQRfJMsuS+FggkbhUqsMgUdwbN1k0ev1LKMPgj0MK66X17YUhhB5uzsT\ngHeMCOFJ0mpiLx9e+pZo34knlTifBtc+ycsmWQ1z3rDI6SYOgxXG71uL0gRgykmm\nKPZpO/bLyCiR5Z2KYVc3rHQU3HTgOu5yLy6c+9C7v/U9AOEGM+iCK65TpjoWc4zd\nQQ4gOsC0p6Hpsk+QLjJg6VfLuQSSaGjlOCZgdbKfd/+RFO+uIEn8 rUAVSNECMWEZ\nXriX7613t2Saer9fwRPvm2L7DWzgVGkWqQPabumDk3F2xmmFghcCAwEAAaNCMEAw\nDgYDVR0PAQH/BAQDAgEGMA8GA1UdEwEB/wQFMAMBAf8wHQYDVR0OBBYEFI/wS3+o\nLkUkrk1Q+mOai97i3Ru8MA0GCSqGSIb3DQEBCwUAA4IBAQBLQNvAUKr+yAzv95ZU\nRUm7lgAJQayzE4aGKAczymvmdLm6AC2upArT9fHxD4q/c2dKg8dEe3jgr25sbwMp\njjM5RcOO5LlXbKr8EpbsU8Yt5CRsuZRj+9xTaGdWPoO4zzUhw8lo/s7awlOqzJCK\n6fBdRoyV3XpYKBovHd7NADdBj+1EbddTKJd+82cEHhXXipa0095MJ6RMG3NzdvQX\nmcIfeg7jLQitChws/zyrVQ4PkX4268NXSb7hLi18YIvDQVETI53O9zJrlAGomecs\nMx86OyXShkDOOyyGeMlhLxS67ttVb9+E7gUJTb0o2HLO02JQZR7rkpeDMdmztcpH\nWD9f\n-----END CERTIFICATE-----"};
        b.b0 = stringArray;
        b.a = false;
        b.g = new Object();
        b.h = new b();
    }
        private b(){
            super();
            this.c = null;
            this.d = null;
            this.e = null;
            this.f = null;
            this.c = new HashSet();
        }
        public static b a(){
            return b.h;
        }

        public PKIXParameters a(Set p0){
            PKIXParameters pKIXParamete1 = null;
            try{
                PKIXParameters pKIXParamete = null;
                if (p0 != null && !p0.isEmpty()) {
                    try{
                        pKIXParamete1 = new PKIXParameters(p0);
                        pKIXParamete1.setRevocationEnabled(false);
                        this.d = pKIXParamete1;
                        pKIXParamete = pKIXParamete1;
                    }catch(Exception e4){
                        pKIXParamete = pKIXParamete1;
                        e4.printStackTrace();
                    }
                }
                return pKIXParamete;
            }catch(Exception e4){
            }
            return null;
        }
        public synchronized void b(){
            int i = 0;
            int i1 = 1;
            if(b.a){
                return;
            }else {
                try{
                    CertificateFactory instance = CertificateFactory.getInstance("X.509");
                    Object g = b.g;
                    synchronized (b.g) {
                        this.f = instance;
                        this.e = CertPathValidator.getInstance("PKIX");
                        X509Certificate[] x509Certific = new X509Certificate[b.b0.length];
                        for (; i < b.b0.length; i = i + 1) {
                            x509Certific[i] = (X509Certificate) instance.generateCertificate(new ByteArrayInputStream(b.b0[i].getBytes(StandardCharsets.ISO_8859_1)));
                            this.c.add(new TrustAnchor(x509Certific[i], null));
                        }
                        this.a(this.c);
                    }
                    b.a = true;
                    return;
                }catch(Throwable e0){
                    e0.printStackTrace();
                    return;
                }
            }
        }
        public PKIXParameters c(){
            Object g = b.g;
            synchronized (b.g) {
                return this.d;
            }
        }
        public CertPathValidator d(){
            synchronized (b.g) {
                return this.e;
            }
        }
        public Set e(){
            synchronized (b.g) {
                return this.c;
            }
        }
        public CertificateFactory f(){
            synchronized (b.g) {
                if (this.f == null) {
                    try{
                        this.f = CertificateFactory.getInstance("X.509");
                    }catch(Exception e1){
                        e1.printStackTrace();
                    }
                }
                return this.f;
            }
        }

    }
