package Cardgame.Core.Interface;


public interface DamageStrategyDecorator extends DamageStrategy {
    DamageStrategyDecorator decorate(DamageStrategy c);

    DamageStrategy removeDecorator(DamageStrategyDecorator d);
}
