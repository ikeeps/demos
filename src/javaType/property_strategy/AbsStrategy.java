package javaType.property_strategy;

public class AbsStrategy implements EffectStrategy {

  public int effect(int value, int coefficient) {
    return value + coefficient;
  }

}
