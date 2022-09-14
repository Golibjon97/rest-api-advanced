package com.epam.esm.tag;

import com.epam.esm.dto.request.TagRequestDto;
import com.epam.esm.dto.response.TagResponseDto;
import com.epam.esm.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface TagService extends BaseService<TagRequestDto, TagResponseDto> {

    List<TagResponseDto> getTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders();

}
