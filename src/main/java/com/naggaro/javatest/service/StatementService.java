package com.naggaro.javatest.service;

import com.naggaro.javatest.dto.RequestDto;
import com.naggaro.javatest.entity.Statement;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */
@Service
public interface StatementService {

     List<Statement> getStatement(RequestDto requestDto, String clientId);

}
