package dev.aequitas.boot;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EventStoreMapper {

    @Select("select * from eventstore where ID = #{id}")
    @Results({
            @Result(column = "event_name", property = "eventName")
    })
    Event findById(@Param("id") Long id);

    @Select("select * from eventstore")
    @Results({
            @Result(column = "event_name", property = "eventName"),
            @Result()
    })
    List<Event> getAll();
}
