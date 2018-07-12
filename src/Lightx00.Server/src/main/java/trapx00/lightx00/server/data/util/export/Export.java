package trapx00.lightx00.server.data.util.export;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记一个类的是Remote对象的工厂类
 * 这个类必须存在无参数的getService方法来返回对象
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Export {

}
