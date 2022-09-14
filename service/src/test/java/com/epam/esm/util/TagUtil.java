package com.epam.esm.util;


import com.epam.esm.domain.Tag;
import com.epam.esm.dto.request.TagRequestDto;
import com.epam.esm.dto.response.TagResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TagUtil {

    public static Tag getTag(){
        return new Tag(
                "tag test"
        );
    }
    public static UUID uuid(String text) {
        return UUID.fromString("00000000-0000-"+text+"-0000-000000000000");
    }

    public static TagRequestDto tagRequestDto() {
        return new TagRequestDto("Tag");
    }

    public static TagResponseDto tagResponseDto() {
        return new TagResponseDto(
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                "Tag",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public static List<TagResponseDto> tagResponseList(){
        List<TagResponseDto> list = new ArrayList<>();
        list.add(tagResponseDto());
        return list;
    }

    public static TagRequestDto updatedTagDTO() {
        return new TagRequestDto("New Tag");
    }

    public static Tag updatedTag() {
        return new Tag(
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "New Tag"
        );
    }

    public static List<Tag> getTags() {
        ArrayList<Tag> tags=new ArrayList<>();

        for (int i = 1; i <=5; i++) {
            Tag tag=new Tag();
            tag.setId(uuid("000".concat(String.valueOf(i))));
            tag.setName("tag".concat(String.valueOf(i)));
            tags.add(tag);
        }
        return tags;
    }

    public static List<TagResponseDto> getTagsTagResponseDtos() {
        ArrayList<TagResponseDto> tags=new ArrayList<>();

        for (int i = 1; i <=5; i++) {
            TagResponseDto tag=new TagResponseDto();
            tag.setId(uuid("000".concat(String.valueOf(i))));
            tag.setName("tag".concat(String.valueOf(i)));
            tags.add(tag);
        }
        return tags;
    }

}
