package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.entity.Borrow;
import com.example.librarymanagementsystem.entity.BorrowState;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BorrowMapper {

    Borrow getBorrowByBookId(Long userId, Long bookId, Enum<BorrowState> state);

    @Insert("insert into borrow (user_id, book_id, book_name, author, image, create_time) " +
            "VALUES (#{userId}, #{id}, #{name}, #{author}, #{image}, now())")
    void borrow(Long userId, Book book);

    List<? extends Borrow> getUserAllBorrow(Long userId, Enum<BorrowState> state);

    List<Borrow> getAllBorrowByBookId(Long id, Enum<BorrowState> state);

    @Update("update borrow set state = #{state}, end_time = #{endTime} " +
            "where user_id = #{userId} and book_id = #{bookId}")
    void repaid(Borrow borrow);

    @Delete("delete from borrow where user_id = #{userId} and book_id = #{bookId}")
    void delete(Borrow borrow);

    @Select("select * from borrow where id = #{id}")
    Borrow getBorrowById(Long id);
}
