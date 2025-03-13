package com.gn.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gn.mvc.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

}
