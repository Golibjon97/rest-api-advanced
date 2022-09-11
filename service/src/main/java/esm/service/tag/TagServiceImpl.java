package esm.service.tag;

import esm.domain.Order;
import esm.domain.Tag;
import esm.dto.request.TagRequestDto;
import esm.dto.response.TagResponseDto;
import esm.repository.order.OrderRepository;
import esm.repository.tag.TagRepository;
import esm.util.PageRequest;
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
