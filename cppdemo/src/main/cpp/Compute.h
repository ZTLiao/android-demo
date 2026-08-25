//
// Created by pc on 2026/8/24.
//

#ifndef ANDROID_DEMO_COMPUTE_H
#define ANDROID_DEMO_COMPUTE_H

template<class T>
class Compute {
private:
    T a;
    T b;
public:
    Compute(T a, T b): a(a), b(b) {

    }

    T add() {
        return a + b;
    }

    T sub() {
        return a - b;
    }
};


#endif //ANDROID_DEMO_COMPUTE_H
