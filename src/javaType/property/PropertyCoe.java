package javaType.property;

import foo.ideas.tx.property_strategy.EffectStrategy;

public class PropertyCoe {
  private int value;
  private int type;
  private EffectStrategy add;

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public int getType() {
    return type;
  }

  public EffectStrategy getAdd() {
    return add;
  }

  public void setAdd(EffectStrategy add) {
    this.add = add;
  }

  public void setType(int type) {
    this.type = type;
  }
  
  public int effect(int v) {
    return this.add.effect(v, value);
  }
}
