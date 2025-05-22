package com.example.librarymanagementsystem.mapper;

import com.example.librarymanagementsystem.entity.Book;
import com.example.librarymanagementsystem.entity.Borrow;
import com.example.librarymanagementsystem.entity.BorrowState;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BorrowMapper {

    Borrow getBorrowByBookId(Long userId, Long bookId, Enum<BorrowState> state);

    @Insert("insert into borrow (user_id, book_id, book_name, author, image, create_time, end_time) " +
            "VALUES (#{userId}, #{book.id}, #{book.name}, #{book.author}, #{book.image}, now(), date_add(now(), interval 7 day))")
    void borrow(Long userId, Book book);

    List<? extends Borrow> records(Long userId, Enum<BorrowState> state, String searchKeyword);

    List<Borrow> getAllBorrowByBookId(Long id, Enum<BorrowState> state);

    @Update("update borrow set state = #{state}, end_time = #{endTime} " +
            "where id = #{id}")
    void repaid(Borrow borrow);

    @Delete("delete from borrow where user_id = #{userId} and book_id = #{bookId}")
    void delete(Borrow borrow);

    @Select("select * from borrow where id = #{id}")
    Borrow getBorrowById(Long id);

    @Insert("insert into borrow (user_id, book_id, book_name, author, image, create_time, end_time, state) " +
            "VALUES (#{borrow.userId}, #{borrow.bookId}, #{borrow.bookName}, #{borrow.author}, " +
            "#{borrow.image}, #{borrow.createTime}, #{borrow.endTime}, #{borrow.state})")
    void adminBorrow(Borrow borrow);
}
