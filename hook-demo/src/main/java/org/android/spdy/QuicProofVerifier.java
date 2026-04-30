package org.android.spdy;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.util.ArrayList;
import java.util.Set;

public class QuicProofVerifier {
    private static String TAG;

    public static int VerifyProof(String p0,String[] p1){
        Object[] objArray;
        CertificateFactory uCertificate;
        if(!b.a){
            return 0;
        }else if((uCertificate = b.a().f()) == null){
            return 0;
        }else {
            ArrayList uArrayList = new ArrayList();
            PKIXParameters pKIXParamete = QuicProofVerifier.getPKIXParametersFromPresetCA();
            CertPathValidator uCertPathVal = b.a().d();
            if (pKIXParamete != null && uCertPathVal != null) {
                try{
                    int len = p1.length;
                    for (int i = 0; i < len; i = i + 1) {
                        uArrayList.add(uCertificate.generateCertificate(new ByteArrayInputStream(p1[i].getBytes(StandardCharsets.ISO_8859_1))));
                    }
                    CertPath uCertPath = (CertPath) uCertificate.generateCertPath(uArrayList);
                    uCertPathVal.validate(uCertPath, pKIXParamete);
                }catch(java.security.cert.CertPathValidatorException e12){
                    e12.printStackTrace();
                }catch(Exception e12){
                    e12.printStackTrace();
                    return 0;
                }
                return 1;
            }else {
                return 0;
            }
        }
    }
    private static PKIXParameters getPKIXParametersFromPresetCA(){
        PKIXParameters pKIXParamete;
        Set set;
        if((pKIXParamete = b.a().c()) == null){
            if ((set = b.a().e()) == null) {
                return null;
            }else {
                pKIXParamete = b.a().a(set);
            }
        }
        return pKIXParamete;
    }


}
