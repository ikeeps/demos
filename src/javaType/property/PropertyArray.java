package javaType.property;

public class PropertyArray {
  private final int[] array;
  public PropertyArray() {
    array = new int[Pro.TYPE_COUNT];
  }

  public void setValue(int type, int value) {
    array[type] = value;
  }

  public void setOneTenThousand(int type, int value) {
    array[type] = value;
  }

  public int addValue(int type, int value) {
    array[type] += value;
    return array[type];
  }
  
  public int effect(PropertyCoe pro) {
    int ori = array[pro.getType()];
    int effected = array[pro.getType()] = pro.effect(ori);
    return effected;
  }
}
