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
    })
    List<Event> getAll();

    @Insert("insert into eventstore (id, uuid, date, event_name, payload) values (#{id}, #{uuid}, #{date}, #{eventName}, #{payload})")
    @SelectKey(statement = "call next value for seq_eventstore", keyProperty = "id", resultType = long.class, before = true)
    int addEvent(Event event);
}
