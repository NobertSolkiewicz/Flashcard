package com.kodilla.Flashcard.mapper;

import com.kodilla.Flashcard.domain.Tag;
import com.kodilla.Flashcard.domain.TagDto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TagMapperTest {
    private final TagMapper tagMapper = new TagMapper();

    @Test
    public void testMapToTag() {
        // Given
        TagDto tagDto = new TagDto(1L, "Test Tag", null);

        // When
        Tag tag = tagMapper.mapToTag(tagDto);

        // Then
        assertEquals(1L, tag.getTagId());
        assertEquals("Test Tag", tag.getName());
    }

    @Test
    public void testMapToTagDto() {
        // Given
        Tag tag = new Tag(1L, "Test Tag", null);

        // When
        TagDto tagDto = tagMapper.mapToTagDto(tag);

        // Then
        assertEquals(1L, tagDto.getTagId());
        assertEquals("Test Tag", tagDto.getName());
        assertNull(tagDto.getFlashcards());
    }

    @Test
    public void testMapToTagDtoList() {
        // Given
        Tag tag1 = new Tag(1L, "Tag 1", null);
        Tag tag2 = new Tag(2L, "Tag 2", null);
        List<Tag> tags = Arrays.asList(tag1, tag2);

        // When
        List<TagDto> tagDtos = tagMapper.mapToTagDtoList(tags);

        // Then
        assertEquals(2, tagDtos.size());
        assertEquals(1L, tagDtos.get(0).getTagId());
        assertEquals("Tag 1", tagDtos.get(0).getName());
        assertNull(tagDtos.get(0).getFlashcards());

        assertEquals(2L, tagDtos.get(1).getTagId());
        assertEquals("Tag 2", tagDtos.get(1).getName());
        assertNull(tagDtos.get(1).getFlashcards());
    }

}
