package com.example.AlumniInternProject.admin.settings.interest;

import com.example.AlumniInternProject.entity.Interest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class InterestServiceImpl implements InterestService {

    private final InterestRepository interestRepository;

    @Override
    public InterestGetDto save(InterestDto interestDto) {
        var interest = new Interest(
                interestDto.getName()
        );
        var saved = interestRepository.save(interest);
        return map(saved);
    }

    @Override
    public List<InterestGetDto> findAll() {
        return interestRepository.findAll()
                .stream()
                .map(interest -> map(interest))
                .collect(Collectors.toList());
    }

    @Override
    public InterestGetDto findById(UUID id) {
        var optional = interestRepository.findById(id);
        if (optional.isPresent()) {
            return map(optional.get());
        }
        throw new RuntimeException("interest not found");
    }

    @Override
    public InterestGetDto update(UUID id, InterestDto dto) {
        var interest = interestRepository.findById(id).orElseThrow(RuntimeException::new);
        interest.setName(dto.getName());
        //
        var saved = interestRepository.save(interest);
        return map(saved);
    }

    @Override
    public void delete(UUID id) {
        interestRepository.deleteById(id);
    }

    private InterestGetDto map(Interest interest) {
        var dto = new InterestGetDto();
        dto.setId(interest.getId());
        dto.setName(interest.getName());
        return dto;
    }
}
