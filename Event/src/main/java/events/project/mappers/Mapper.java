package events.project.mappers;


public interface Mapper<T, R> {
    R map(T t);
}