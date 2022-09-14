package com.epam.esm.tag;

import com.epam.esm.domain.Order;
import com.epam.esm.domain.Tag;
import com.epam.esm.dto.request.TagRequestDto;
import com.epam.esm.dto.response.TagResponseDto;
import com.epam.esm.repository.order.OrderRepository;
import com.epam.esm.repository.tag.TagRepository;
import com.epam.esm.util.PageRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public TagServiceImpl(TagRepository tagRepository, OrderRepository orderRepository,
                          ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TagResponseDto create(TagRequestDto tagRequestDto) {
        Tag tag = modelMapper.map(tagRequestDto, Tag.class);
        Tag saved = tagRepository.create(tag);
        return modelMapper.map(saved, TagResponseDto.class);
    }

    @Override
    public TagResponseDto get(UUID id) {
        Tag tag = tagRepository.getOne(id);
        return modelMapper.map(tag, TagResponseDto.class);
    }

    @Override
    public List<TagResponseDto> getAll(PageRequest pageRequest) {
        List<Tag> tags = tagRepository.getAll(pageRequest);

        return modelMapper.map(tags, new TypeToken<List<TagResponseDto>>() {
        }.getType());
    }

    @Override
    public int delete(UUID id) {
        return tagRepository.delete(id);
    }

    @Transactional
    @Override
    public List<TagResponseDto> getTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders() {
        Order order = orderRepository.getTheMostExpensive();
        List<Tag> tags = tagRepository.getMostUsedTag(order.getUser().getId());
        return modelMapper.map(tags, new TypeToken<List<TagResponseDto>>() {
        }.getType());
    }

}
