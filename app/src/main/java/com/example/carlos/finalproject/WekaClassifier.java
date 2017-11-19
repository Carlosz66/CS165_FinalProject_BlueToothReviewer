package com.example.carlos.finalproject;

/**
 * Created by RZ on 11/19/17.
 */

//classifier need to be updated later
class WekaClassifier {

    public static double classify(Object[] i)
            throws Exception {

        double p = Double.NaN;
        p = WekaClassifier.Nefb89ed0(i);
        return p;
    }
    static double Nefb89ed0(Object []i) {
        double p = Double.NaN;
        if (i[64] == null) {
            p = 2;
        } else if (((Double) i[64]).doubleValue() <= 0.32617) {
            p = WekaClassifier.N1f90cd711(i);
        } else if (((Double) i[64]).doubleValue() > 0.32617) {
            p = WekaClassifier.N4b84a0f04(i);
        }
        return p;
    }
    static double N1f90cd711(Object []i) {
        double p = Double.NaN;
        if (i[22] == null) {
            p = 2;
        } else if (((Double) i[22]).doubleValue() <= 0.77607) {
            p = WekaClassifier.N7a0e04c12(i);
        } else if (((Double) i[22]).doubleValue() > 0.77607) {
            p = 3;
        }
        return p;
    }
    static double N7a0e04c12(Object []i) {
        double p = Double.NaN;
        if (i[27] == null) {
            p = 2;
        } else if (((Double) i[27]).doubleValue() <= 0.215641) {
            p = WekaClassifier.N1962f6d03(i);
        } else if (((Double) i[27]).doubleValue() > 0.215641) {
            p = 2;
        }
        return p;
    }
    static double N1962f6d03(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 3;
        } else if (((Double) i[2]).doubleValue() <= 0.595347) {
            p = 3;
        } else if (((Double) i[2]).doubleValue() > 0.595347) {
            p = 2;
        }
        return p;
    }
    static double N4b84a0f04(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 1;
        } else if (((Double) i[3]).doubleValue() <= 9.291583) {
            p = WekaClassifier.N526617b45(i);
        } else if (((Double) i[3]).doubleValue() > 9.291583) {
            p = 3;
        }
        return p;
    }
    static double N526617b45(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 3;
        } else if (((Double) i[0]).doubleValue() <= 26.096612) {
            p = 3;
        } else if (((Double) i[0]).doubleValue() > 26.096612) {
            p = WekaClassifier.N584439356(i);
        }
        return p;
    }
    static double N584439356(Object []i) {
        double p = Double.NaN;
        if (i[8] == null) {
            p = 1;
        } else if (((Double) i[8]).doubleValue() <= 2.57181) {
            p = WekaClassifier.N569e909b7(i);
        } else if (((Double) i[8]).doubleValue() > 2.57181) {
            p = WekaClassifier.N233e456810(i);
        }
        return p;
    }
    static double N569e909b7(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 46.116149) {
            p = WekaClassifier.N387339988(i);
        } else if (((Double) i[0]).doubleValue() > 46.116149) {
            p = 1;
        }
        return p;
    }
    static double N387339988(Object []i) {
        double p = Double.NaN;
        if (i[23] == null) {
            p = 1;
        } else if (((Double) i[23]).doubleValue() <= 0.727618) {
            p = WekaClassifier.N5aca52959(i);
        } else if (((Double) i[23]).doubleValue() > 0.727618) {
            p = 3;
        }
        return p;
    }
    static double N5aca52959(Object []i) {
        double p = Double.NaN;
        if (i[32] == null) {
            p = 3;
        } else if (((Double) i[32]).doubleValue() <= 0.048697) {
            p = 3;
        } else if (((Double) i[32]).doubleValue() > 0.048697) {
            p = 1;
        }
        return p;
    }
    static double N233e456810(Object []i) {
        double p = Double.NaN;
        if (i[20] == null) {
            p = 3;
        } else if (((Double) i[20]).doubleValue() <= 0.930384) {
            p = 3;
        } else if (((Double) i[20]).doubleValue() > 0.930384) {
            p = 1;
        }
        return p;
    }
}