package esm.validation.constraints;

public interface Updatable<T> {

    void checkUpdate(T t);
}
