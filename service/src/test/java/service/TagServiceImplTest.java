package service;

import esm.domain.Order;
import esm.domain.Tag;
import esm.dto.request.TagRequestDto;
import esm.dto.response.TagResponseDto;
import esm.repository.order.OrderRepository;
import esm.repository.tag.TagRepository;
import esm.service.tag.TagServiceImpl;
import esm.util.PageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import service.util.CertificateUtil;
import service.util.OrderUtil;
import service.util.TagUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private TagServiceImpl tagService;
    private Tag tag;
    private List<Tag> tagList;
    private Order order;
    private TagRequestDto requestDto;
    private TagResponseDto responseDto;
    private List<TagResponseDto> responseList;
    private PageRequest pageRequest;

    @BeforeEach
    void setUp() {
        tag = TagUtil.getTag();
        tagList = TagUtil.getTags();
        requestDto = TagUtil.tagRequestDto();
        responseDto = TagUtil.tagResponseDto();
        responseList = TagUtil.tagResponseList();
        pageRequest = CertificateUtil.getPageRequest();
        order = OrderUtil.getOrder();
    }

    @Test
    void canCreateTag(){
        when(modelMapper.map(requestDto, Tag.class)).thenReturn(tag);
        when(tagRepository.create(tag)).thenReturn(tag);
        when(modelMapper.map(tag, TagResponseDto.class)).thenReturn(responseDto);
        TagResponseDto result = tagService.create(requestDto);
        assertEquals(responseDto, result);
    }

    @Test
    void canGet() {
        when(tagRepository.getOne(tag.getId())).thenReturn(tag);
        when(modelMapper.map(tag, TagResponseDto.class)).thenReturn(responseDto);
        TagResponseDto result = tagService.get(tag.getId());
        assertEquals(responseDto, result);
    }

    @Test
    void canGetAll() {
        when(tagRepository.getAll(pageRequest)).thenReturn(tagList);
        when(modelMapper.map(tagList, new TypeToken<List<TagResponseDto>>() {}.getType()))
                .thenReturn(responseList);
        List<TagResponseDto> result = tagService.getAll(pageRequest);
        assertNotNull(result);
    }

    @Test
    void canDelete() {
        when(tagRepository.delete(tag.getId())).thenReturn(1);
        int result = tagService.delete(tag.getId());
        assertEquals(1, result);
    }

    @Test
    void canGetTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders() {
        when(orderRepository.getTheMostExpensive()).thenReturn(order);
        when(tagRepository.getMostUsedTag(order.getUser().getId())).thenReturn(tagList);
        when(modelMapper.map(tagList, new TypeToken<List<TagResponseDto>>() {
                }.getType())).thenReturn(responseList);
        List<TagResponseDto> result = tagService.getTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders();
        assertNotNull(result);
    }
}