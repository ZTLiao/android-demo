package org.android.spdy;

import java.util.Map;

public interface SessionCustomExtraCb {

    void onCustomFrameRecvCallback(SpdySession p0, Object p1, int p2, int p3, int p4, int p5, byte[] p6, SuperviseData p7, Map p8);
}
