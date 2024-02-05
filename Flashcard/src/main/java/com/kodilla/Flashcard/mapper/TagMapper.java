package com.kodilla.Flashcard.mapper;

import com.kodilla.Flashcard.domain.Tag;
import com.kodilla.Flashcard.domain.TagDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagMapper {

    public Tag mapToTag(final TagDto tagDto) {
        Tag tag = new Tag();
        tag.setTagId(tagDto.getTagId());
        tag.setName(tagDto.getName());
        return tag;
    }

    public TagDto mapToTagDto(final Tag tag) {
        return new TagDto(
                tag.getTagId(),
                tag.getName(),
                tag.getFlashcards()
        );
    }

    public List<TagDto> mapToTagDtoList(final List<Tag> tags) {
        return tags.stream()
                .map(this::mapToTagDto)
                .collect(Collectors.toList());
    }
}
