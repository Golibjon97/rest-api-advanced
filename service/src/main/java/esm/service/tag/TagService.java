package esm.service.tag;

import esm.dto.request.TagRequestDto;
import esm.dto.response.TagResponseDto;
import esm.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface TagService extends BaseService<TagRequestDto, TagResponseDto> {

    List<TagResponseDto> getTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders();

}
