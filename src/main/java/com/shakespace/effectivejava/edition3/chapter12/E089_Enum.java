package com.shakespace.effectivejava.edition3.chapter12;

import java.util.Arrays;

/**
 * 对于实例控制，枚举类型优于 readResolve
 * 如果实现 Serializable 接口，该类将不再是单例的。
 * 类使用默认序列化形式还是自定义序列化形式并不重要（Item-87），类是否提供显式 readObject 方法也不重要（Item-88）。
 * 任何 readObject 方法，不管是显式的还是默认的，都会返回一个新创建的实例，这个实例与类初始化时创建的实例不同。
 * <p>
 * readResolve 的可访问性非常重要。 如果你将 readResolve 方法放在 final 类上，那么它应该是私有的。
 * 如果将 readResolve 方法放在非 final 类上，必须仔细考虑其可访问性。如果它是私有的，它将不应用于任何子类。
 * 如果它是包级私有的，它将只适用于同一包中的子类。
 * 如果它是受保护的或公共的，它将应用于不覆盖它的所有子类。如果 readResolve 方法是受保护的或公共的，
 * 而子类没有覆盖它，反序列化子类实例将生成超类实例，这可能会导致 ClassCastException。
 * <p>
 * 在可能的情况下，使用枚举类型强制实例控制不变量。
 * 如果这是不可能的，并且你需要一个既可序列化又实例控制的类，那么你必须提供一个 readResolve 方法，
 * 并确保该类的所有实例字段都是基本类型，或使用 transient 修饰。
 */
public class E089_Enum {
}

class Elvis {
    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {
    }

    public void leaveTheBuilding() {
    }

    /**
     * 此方法忽略反序列化对象，返回初始化类时创建的特殊 Elvis 实例。
     * 因此，Elvis 实例的序列化形式不需要包含任何实际数据；
     * 所有实例字段都应该声明为 transient。
     * 事实上，如果你依赖 readResolve 进行实例控制，那么所有具有对象引用类型的实例字段都必须声明为 transient。
     */
    // 如果 Elvis 类要实现 Serializable 接口，下面的 readResolve 方法就足以保证其单例属性：
    // readResolve for instance control - you can do better!
    private Object readResolve() {
        // Return the one true Elvis and let the garbage collector
        // take care of the Elvis impersonator.
        return INSTANCE;
    }
}

// Enum singleton - the preferred approach
enum Elvis2 {
    INSTANCE;
    private String[] favoriteSongs = {"Hound Dog", "Heartbreak Hotel"};

    public void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }
}
