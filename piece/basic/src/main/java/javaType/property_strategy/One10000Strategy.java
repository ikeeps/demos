package javaType.property_strategy;

public class One10000Strategy implements EffectStrategy {

  public int effect(int value, int coefficient) {
    return value + value * coefficient / 10000;
  }

}
