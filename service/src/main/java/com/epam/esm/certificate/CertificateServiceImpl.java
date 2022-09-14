package com.epam.esm.certificate;

import com.epam.esm.domain.Certificate;
import com.epam.esm.domain.Tag;
import com.epam.esm.dto.request.CertificateRequestDto;
import com.epam.esm.dto.response.CertificateResponseDto;
import com.epam.esm.exception.DataNotFoundException;
import com.epam.esm.exception.InvalidTagException;
import com.epam.esm.repository.certificate.CertificateRepository;
import com.epam.esm.repository.tag.TagRepository;
import com.epam.esm.util.PageRequest;
import com.epam.esm.validation.CertificateValidator;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;
    private final CertificateValidator certificateValidator;

    public CertificateServiceImpl(CertificateRepository certificateRepository, TagRepository tagRepository, ModelMapper modelMapper, CertificateValidator certificateValidator) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
        this.certificateValidator = certificateValidator;
    }

    @Transactional
    @Override
    public CertificateResponseDto create(CertificateRequestDto certificateRequestDto) {
        certificateValidator.checkNotNull(certificateRequestDto);
        if (certificateRequestDto.getTags() != null && !certificateRequestDto.getTags().isEmpty()) {
            List<Tag> tags = certificateRequestDto.getTags();
            certificateRequestDto.setTags(createTags(tags));
        }
        Certificate certificate = modelMapper.map(certificateRequestDto, Certificate.class);
        Certificate saved = certificateRepository.create(certificate);
        return modelMapper.map(saved, CertificateResponseDto.class);
    }


    @Override
    public CertificateResponseDto get(UUID id) {
        Certificate certificate = certificateRepository.getOne(id);
        return modelMapper.map(certificate, CertificateResponseDto.class);
    }

    @Override
    public List<CertificateResponseDto> getAll(PageRequest pageRequest) {
        List<Certificate> certificates = certificateRepository.getAll(pageRequest);

        return modelMapper.map(certificates, new TypeToken<List<CertificateResponseDto>>() {
        }.getType());
    }

    @Transactional
    @Override
    public int delete(UUID id) {
        return certificateRepository.delete(id);
    }

    @Override
    public List<CertificateResponseDto> getByMultipleTags(List<String> tags, PageRequest pageRequest) {
        List<Tag> list = tags.stream().map(
                s -> tagRepository.findByName(s).orElseThrow(() ->
                        new DataNotFoundException("Tag with name:(" + s + " not found)")))
                .collect(Collectors.toList()
        );
        List<Certificate> certificates = certificateRepository.getByMultipleTags(
                pageRequest, list.toArray(new Tag[0])
        );

        return modelMapper.map(certificates, new TypeToken<List<CertificateResponseDto>>() {}.getType());
    }

    @Override
    public List<CertificateResponseDto> get(String searchWord, String tagName, PageRequest pageRequest) {
        if (searchWord == null && tagName == null) {
            return getAll(pageRequest);
        }
        UUID tagId = null;
        Optional<Tag> tag = tagRepository.findByName(tagName);
        if (tag.isPresent()){
            tagId=tag.get().getId();
        }

        List<Certificate> certificates = certificateRepository.getBy(searchWord, tagId, pageRequest);

        return modelMapper.map(certificates, new TypeToken<List<CertificateResponseDto>>() {
        }.getType());
    }

    @Transactional
    @Override
    public CertificateResponseDto update(CertificateRequestDto requestDto, UUID id) {
        certificateValidator.checkUpdate(requestDto);
        if (requestDto.getTags() != null && !requestDto.getTags().isEmpty()) {
            List<Tag> tags = requestDto.getTags();
            requestDto.setTags(createTags(tags));
        }
        Certificate oldCertificate = certificateRepository.getOne(id);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(requestDto, oldCertificate);

        certificateValidator.validate(modelMapper.map(oldCertificate, CertificateRequestDto.class));

        Certificate updated = certificateRepository.update(oldCertificate);
        return modelMapper.map(updated, CertificateResponseDto.class);
    }
    private List<Tag> createTags(List<Tag> tagEntities) {
        List<Tag> tagEntityList = new ArrayList<>();
        tagEntities.forEach(tag -> {
            if (tag.getName() == null || tag.getName().isEmpty()) {
                throw new InvalidTagException("tag name cannot be empty or null");
            }
            Optional<Tag> byName = tagRepository.findByName(tag.getName());
            if (byName.isPresent()) {
                tagEntityList.add(byName.get());
            } else {
                tagEntityList.add(tagRepository.create(tag));
            }
        });
        return tagEntityList;
    }

}
