package com.naggaro.javatest.service.impl;

import com.naggaro.javatest.constants.NaggaroConstants;
import com.naggaro.javatest.dto.RequestDto;
import com.naggaro.javatest.entity.Statement;
import com.naggaro.javatest.exception.UnAutherizeException;
import com.naggaro.javatest.repository.StatementRepository;
import com.naggaro.javatest.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */
@Service
public class StatementServiceImpl implements StatementService {

    @Autowired
    public StatementRepository statementRepository;


    @Override
    public List<Statement> getStatement(RequestDto requestDto, String clientId) {

        //  - If the request does not specify any parameter, then the search will return three months
        //     back statement.

//        The account number should be hashed before sent to the user.

        Boolean isAdmin = false;

        if(clientId.equalsIgnoreCase("admin"))
            isAdmin=true;

        List<Statement> response= statementRepository.findAll(getSpecification(requestDto,isAdmin));
        if(response!=null)
        {
            return response;
        }
        return null;
    }


    private Specification getSpecification(RequestDto requestDto,Boolean isAdmin)  {
        Specification specification = (Specification<Statement>) (root, query, criteriaBuilder) -> {


            List<Predicate> predicates  = getUserPredicates(requestDto,criteriaBuilder,root,isAdmin);


            query.orderBy(criteriaBuilder.desc(root.get("id")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return specification;
    }
    public List<Predicate> getUserPredicates(RequestDto requestDto, CriteriaBuilder criteriaBuilder, Root<Statement> root,Boolean isAdmin)  {

        if(requestDto==null)
        {
            isAdmin=false;
            requestDto = new RequestDto();
        }

        if(isAdmin)
            return getAdminPredicates(requestDto,criteriaBuilder,root);


        // is not admin
        if(!requestDto.isEmpty())
        {
          throw new UnAutherizeException(NaggaroConstants.ACCESS_DENIED);
        }

        List<Predicate> predicates = new ArrayList<>();


        requestDto.setFromDate(DateTimeFormatter.ofPattern(NaggaroConstants.DATE_TIME_FORMATE, Locale.ENGLISH).format(LocalDateTime.now().minusMonths(NaggaroConstants.STATMENT_DEFAULT_TIME_IN_MONTHS)));
        requestDto.setToDate(DateTimeFormatter.ofPattern(NaggaroConstants.DATE_TIME_FORMATE, Locale.ENGLISH).format(LocalDateTime.now()));


        settingDatePredicates(requestDto, criteriaBuilder, root, predicates);


        return predicates;
    }

    private void settingDatePredicates(RequestDto requestDto, CriteriaBuilder criteriaBuilder, Root<Statement> root, List<Predicate> predicates) {
        try {

            String fromDateString  = requestDto.getFromDate();

            if (fromDateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
                fromDateString = fromDateString+" 00:00:00";
            }

            Date toDate = new Date();
            Date fromDate=new SimpleDateFormat(NaggaroConstants.DATE_TIME_FORMATE).parse(fromDateString);

            if(requestDto.getToDate()!=null)
            {
                String toDateString  = requestDto.getToDate();
                if (toDateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    toDateString = toDateString+" 23:59:00";
                }
                toDate=new SimpleDateFormat(NaggaroConstants.DATE_TIME_FORMATE).parse(toDateString);
            }


            Predicate date =  criteriaBuilder.between(root.get("datefield"),fromDate, toDate);
            predicates.add(date);

        } catch (ParseException e) {
//                logger.error("Error while parsing toData and fromDate ",e);
            System.out.println(e.getMessage());
        }
    }

    public List<Predicate> getAdminPredicates(RequestDto requestDto, CriteriaBuilder criteriaBuilder, Root<Statement> root) {
        List<Predicate> predicates = new ArrayList<>();

        if(requestDto.getToDate()==null && requestDto.getFromDate()==null)
        {
            requestDto.setFromDate(DateTimeFormatter.ofPattern(NaggaroConstants.DATE_TIME_FORMATE, Locale.ENGLISH).format(LocalDateTime.now().minusMonths(NaggaroConstants.STATMENT_DEFAULT_TIME_IN_MONTHS)));
            requestDto.setToDate(DateTimeFormatter.ofPattern(NaggaroConstants.DATE_TIME_FORMATE, Locale.ENGLISH).format(LocalDateTime.now()));
        }

        if(requestDto.getFromDate()!=null)
        {
            settingDatePredicates(requestDto, criteriaBuilder, root, predicates);
        }

        if(requestDto.getAccountId()!=null)
        {
            Predicate predicate = criteriaBuilder.equal(root.get("accountId"), requestDto.getAccountId());
            predicates.add(predicate);
        }
        if(requestDto.getToAmount()!=null && requestDto.getFromAmount()!=null)
        {
            Predicate predicate = criteriaBuilder.between(root.get("amount"),requestDto.getFromAmount(),requestDto.getToAmount());
            predicates.add(predicate);
        }


        return predicates;
    }
}
