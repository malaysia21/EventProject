package events.project.other;


public interface Mapper<T, R> {

    R map(T t);

}