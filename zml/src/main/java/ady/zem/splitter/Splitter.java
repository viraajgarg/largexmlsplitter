package ady.zem.splitter;

import java.util.List;
@FunctionalInterface
public interface Splitter<R, I> {
	public List<R> split(I criteria) throws Exception;
}
