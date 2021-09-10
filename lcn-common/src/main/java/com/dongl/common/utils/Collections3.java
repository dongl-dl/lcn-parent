package com.dongl.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName Collections3.java
 * @Description 集合运算----这个可以使用 GoogleGuava
 * @createTime 2021-09-09 09:46:00
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Collections3 {

	/**
	 * 返回a+b的新List.
	 *
	 * @param <T> the type parameter
	 * @param a   the a
	 * @param b   the b
	 *
	 * @return the list
	 */
	public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
		List<T> result = new ArrayList<>(a);
		result.addAll(b);
		return result;
	}

	/**
	 * 返回a-b的新List.
	 *
	 * @param <T> the type parameter
	 * @param a   the a
	 * @param b   the b
	 *
	 * @return the list
	 */
	public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
		List<T> list = new ArrayList<>(a);
		for (T element : b) {
			list.remove(element);
		}

		return list;
	}

	/**
	 * 返回a与b的交集的新List.
	 *
	 * @param <T> the type parameter
	 * @param a   the a
	 * @param b   the b
	 *
	 * @return the list
	 */
	public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
		List<T> list = new ArrayList<>();

		for (T element : a) {
			if (b.contains(element)) {
				list.add(element);
			}
		}
		return list;
	}
}
