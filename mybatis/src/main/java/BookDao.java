import org.apache.ibatis.annotations.Param;

public interface BookDao {
    Book selectBook(@Param("TABLE") String TABLE, @Param("id") int id);
}
