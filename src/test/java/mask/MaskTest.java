package mask;


import org.junit.jupiter.api.Test;

class MaskTest {

    @Test
    void getIdMask() {
        System.out.println(Mask.getIdMask("666666123456784444"));
        System.out.println(Mask.getIdMask("666666123456333"));
    }

    @Test
    void getPhoneMask() {
        System.out.println(Mask.getPhoneMask(" 12344444444 "));
        System.out.println(Mask.getPhoneMask("1234444444423"));
    }

    @Test
    void getEmailMask() {
        System.out.println(Mask.getEmailMask(" 12344444444@111.com "));
    }
}