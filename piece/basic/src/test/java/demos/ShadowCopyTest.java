package demos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 * ShadowCopyTest
 * 
 * @author hujianfeng
 * @date 2018/06/21
 *
 */
/**
 * 
 * ShadowCopyTest
 * 
 * @author hujianfeng
 * @date 2018/06/21
 *
 */
public class ShadowCopyTest {
    @Test
    public void testShadowCopy() throws CloneNotSupportedException {
        Entity entity = new Entity();
        entity.setValue("a");
        
        Entity2 entity2 = new Entity2();
        entity2.setValue("a in 2");
        
        entity.setValue2(entity2);
        
        Entity clone = (Entity) entity.clone();
        clone.setValue("b");
//        assertEquals("a", entity.getValue());
        
        clone.getValue2().setValue("b in 2");
        
//        assertEquals("a in 2", entity.getValue2().getValue());
//      TODO ImmutableList
    }

    static class Entity implements Cloneable {
        private String value;
        private Entity2 value2;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Entity2 getValue2() {
            return value2;
        }

        public void setValue2(Entity2 value2) {
            this.value2 = value2;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
        
    }

    static class Entity2 {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }
}
