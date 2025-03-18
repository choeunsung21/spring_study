package com.gn.mvc.specification;

import org.springframework.data.jpa.domain.Specification;

import com.gn.mvc.entity.Attach;
import com.gn.mvc.entity.Board;

public class AttachSpecification {
	
	public static Specification<Attach> boardEquals(Board board){
		return (root, query, CriteriaBuilder)
				-> CriteriaBuilder.equal(root.get("board"), board);
	}
}
