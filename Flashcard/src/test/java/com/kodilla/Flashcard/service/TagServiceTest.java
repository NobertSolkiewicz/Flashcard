package com.kodilla.Flashcard.service;

import com.kodilla.Flashcard.domain.Tag;
import com.kodilla.Flashcard.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTags() {
        // Given
        Tag tag1 = new Tag(1L, "Tag 1", new ArrayList<>());
        Tag tag2 = new Tag(2L, "Tag 2", new ArrayList<>());
        when(tagRepository.findAll()).thenReturn(Arrays.asList(tag1, tag2));

        // When
        List<Tag> tags = tagService.getAllTags();

        // Then
        assertEquals(2, tags.size());
        assertEquals("Tag 1", tags.get(0).getName());
        assertEquals("Tag 2", tags.get(1).getName());
    }

    @Test
    public void testGetTagById() {
        // Given
        Tag tag = new Tag(1L, "Tag 1", new ArrayList<>());
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));

        // When
        Tag retrievedTag = tagService.getTagById(1L);

        // Then
        assertNotNull(retrievedTag);
        assertEquals("Tag 1", retrievedTag.getName());
    }

    @Test
    public void testGetTagByIdTagNotFound() {
        // Given
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(NoSuchElementException.class, () -> tagService.getTagById(1L));
    }

    @Test
    public void testSaveTag() {
        // Given
        Tag tag = new Tag(1L, "Tag 1", new ArrayList<>());
        when(tagRepository.save(tag)).thenReturn(tag);

        // When
        Tag savedTag = tagService.saveTag(tag);

        // Then
        assertNotNull(savedTag);
        assertEquals("Tag 1", savedTag.getName());
    }

    @Test
    public void testDeleteTag() {
        // Given
        doNothing().when(tagRepository).deleteById(1L);

        // When
        tagService.deleteTag(1L);

        // Then
        verify(tagRepository, times(1)).deleteById(1L);
    }
}
