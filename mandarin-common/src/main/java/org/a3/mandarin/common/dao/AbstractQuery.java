package org.a3.mandarin.common.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractQuery<T> {
    private Logger logger= LoggerFactory.getLogger(AbstractQuery.class);
    private List<LogicSpecificationPair> logicSpecificationPairs;
    private LogicType combineLogicType;

    protected AbstractQuery() {
        combineLogicType = LogicType.AND;
    }

    public LogicType getCombineLogicType() {
        return combineLogicType;
    }

    public void setCombineLogicType(LogicType combineLogicType) {
        this.combineLogicType = combineLogicType;
    }

    public Specification<T> toSpec(){
        Specification<T> spec=this.toSpecWithLogicType(combineLogicType);

        if (logicSpecificationPairs==null)
            return spec;
        for (LogicSpecificationPair pair: this.logicSpecificationPairs){
            switch (pair.type){
                case AND:
                    spec.and(pair.specification);
                    break;
                case OR:
                    spec.or(pair.specification);
                    break;
            }
        }
        return spec;
    }

    protected void addPredicate(List<Predicate> predicates, Field field, QueryWord qw, CriteriaBuilder cb,
                                Object value, Root<T> root){
        String fieldName=field.getName();
        String column=qw.column();
        Path path=root.get(column);

        switch (qw.type()){
            case LIKE:
                predicates.add(cb.like(path, "%"+ value +"%"));
                break;
            case EQUAL:
                predicates.add(cb.equal(path, value));
                break;
            case NOT_LIKE:
                predicates.add(cb.notLike(path, (String)value));
                break;
            case LESS_THAN:
                // why not Comparable<T> ?
                predicates.add(cb.greaterThan(path, (Comparable)value));
                break;
            case GREATER_THAN:
                predicates.add(cb.lessThan(path, (Comparable)value));
                break;
        }
    }

    protected Specification<T> toSpecWithLogicType(LogicType type){
        AbstractQuery outer=this;
        return (root, query, cb) -> {
            Class clazz=outer.getClass();
            logger.debug("class :"+clazz.getName());
            List<Field> fields=this.getRecursionDeclaredFields(clazz);
            List<Predicate> predicates=new ArrayList<>();
            logger.debug("fields :"+fields.toString());

            for (Field field: fields){
                QueryWord qw=field.getAnnotation(QueryWord.class);
                if (qw==null) continue;

                field.setAccessible(true);
                try {
                    Object value=field.get(outer);
                    if (value==null) continue;
                    logger.debug("Field Value "+value.toString());

                    if (value instanceof List){
                        List list=(List) value;
                        for (Object elem: list)
                            addPredicate(predicates, field, qw, cb, elem, root);
                    }else {
                        addPredicate(predicates, field, qw, cb, value, root);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            Predicate result=null;
            if (type==LogicType.AND)
                result = cb.and(predicates.toArray(new Predicate[predicates.size()]));
            else if (type==LogicType.OR)
                result = cb.or(predicates.toArray(new Predicate[predicates.size()]));
            return result;
        };
    }

    private List<Field> getRecursionDeclaredFields(Class<T> clazz){
        if (clazz==Object.class) return new ArrayList<>();

        List<Field> fields=new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        fields.addAll(getRecursionDeclaredFields((Class)clazz.getSuperclass()));
        return fields;
    }

    /*
     * you can use method nested, if write method like this,
     *   A method(B),
     * while A can downcast into B that is, A->B naturally;
     *
     * */

    public static <T> Specification<T> combineWithLogicType(Specification<T> specA, Specification<T> specB, LogicType type){
        switch (type){
            case AND:
                return combineWithAndLogic(specA, specB);
            case OR:
                return combineWithOrLogic(specA, specB);
            case XOR:
                return combineWithXorLogic(specA, specB);
        }
        return null;
    }

    public static <T> Specification<T> withNotLogic(Specification<T> spec){
        return Specification.not(spec);
    }

    public static <T> Specification<T> combineWithAndLogic(Specification<T> specA, Specification<T> specB){
        return specA.and(specB);
    }

    public static <T> Specification<T> combineWithOrLogic(Specification<T> specA, Specification<T> specB){
        return specA.or(specB);
    }

    public static <T> Specification<T> combineWithXorLogic(Specification<T> specA, Specification<T> specB){
        return combineWithOrLogic(
                specA.and(Specification.not(specB)),
                specB.and(Specification.not(specA))
        );
    }

    public static <T> Specification combineWithLogicType(AbstractQuery eqA, AbstractQuery eqB, LogicType type){
        Specification specA=eqA.toSpec(), specB=eqB.toSpec();
        switch (type){
            case AND:
                return combineWithAndLogic(specA, specB);
            case OR:
                return combineWithOrLogic(specA, specB);
            case XOR:
                return combineWithXorLogic(specA, specB);
        }
        return null;
    }

    public static <T> Specification<T> withNotLogic(AbstractQuery eq){
        return Specification.not(eq.toSpec());
    }

    public static <T> Specification<T> combineWithAndLogic(AbstractQuery<T> eqA, AbstractQuery<T> eqB){
        return eqA.toSpec().and(eqB.toSpec());
    }

    public static <T> Specification<T> combineWithOrLogic(AbstractQuery<T> eqA, AbstractQuery<T> eqB){
        return eqA.toSpec().or(eqB.toSpec());
    }

    public static <T> Specification<T> combineWithXorLogic(AbstractQuery eqA, AbstractQuery eqB){
        return combineWithXorLogic(eqA.toSpec(), eqB.toSpec());
    }

    public AbstractQuery<T> and(AbstractQuery<T> eq){
        if (logicSpecificationPairs==null)
            logicSpecificationPairs=new ArrayList<>();
        this.logicSpecificationPairs.add(new LogicSpecificationPair(LogicType.AND, eq.toSpec()));
        return this;
    }

    public AbstractQuery<T> or(AbstractQuery<T> eq){
        if (logicSpecificationPairs==null)
            logicSpecificationPairs=new ArrayList<>();
        this.logicSpecificationPairs.add(new LogicSpecificationPair(LogicType.OR, eq.toSpec()));
        return this;
    }

    protected class LogicSpecificationPair {
        private LogicType type;
        private Specification<T> specification;

        public LogicSpecificationPair(LogicType type, Specification<T> specification) {
            this.type = type;
            this.specification = specification;
        }

        public LogicType getType() {
            return type;
        }

        public void setType(LogicType type) {
            this.type = type;
        }

        public Specification<T> getSpecification() {
            return specification;
        }

        public void setSpecification(Specification<T> specification) {
            this.specification = specification;
        }
    }

    public enum MatchType {
        EQUAL,
        LIKE,
        NOT_LIKE,
        GREATER_THAN,
        LESS_THAN
    }

    public enum LogicType {
        AND,
        OR,
        XOR,
        NOT
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface QueryWord {
        String column() default "";
        MatchType type() default MatchType.EQUAL;
    }
}
