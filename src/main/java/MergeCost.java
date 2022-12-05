import java.util.ArrayList;
import java.util.List;

public class MergeCost {

    public static List<Cost> addNewCosts(List<Cost> current, List<Cost> input) {
        List<Cost> result = new ArrayList<>(current);
        for (Cost inCost : input) {
            boolean needToAdd = false;
            for (Cost CurCost : current) {
                if (CurCost.getProduct_code().equals(inCost.getProduct_code())
                        && CurCost.getDepart() == inCost.getDepart()
                        && CurCost.getNumber() == inCost.getNumber()) {
                    if (inCost.getBegin().compareTo(CurCost.getBegin()) > 0 && inCost.getEnd().compareTo(CurCost.getEnd()) < 0) {
                        if (CurCost.getValue() != inCost.getValue()) {
                            inside(result, inCost, CurCost);
                        }
                        needToAdd = true;
                        break;
                    } else if (inCost.getBegin().compareTo(CurCost.getBegin()) <= 0 && inCost.getEnd().compareTo(CurCost.getEnd()) >= 0) {
                        result.remove(CurCost);
                        if (inCost.getEnd().compareTo(CurCost.getEnd()) == 0) {
                            result.add(inCost);
                            needToAdd = true;
                            break;
                        }
                    } else if (inCost.getBegin().compareTo(CurCost.getBegin()) > 0 && inCost.getEnd().compareTo(CurCost.getEnd()) >= 0) {
                        if (inCost.getEnd().compareTo(CurCost.getEnd()) == 0) {
                            if (CurCost.getValue() != inCost.getValue()) {
                                CurCost.setEnd(inCost.getEnd());
                                result.add(inCost);
                            }
                            needToAdd = true;
                            break;
                        } else {
                            ahead(result, inCost, CurCost);
                        }
                    } else if (inCost.getBegin().compareTo(CurCost.getBegin()) <= 0 && inCost.getEnd().compareTo(CurCost.getEnd()) < 0) {
                        last(result, inCost, CurCost);
                        needToAdd = true;
                        break;
                    }
                }
            }
            if (!needToAdd) {
                result.add(inCost);
            }
        }
        return result;
    }

    /**
     * Метод добавляет в коллекцию новую цену внутрь существующец. С разделением существующей на две новых.
     */
    private static void inside(List<Cost> result, Cost inCost , Cost CurCost) {
        int index = result.indexOf(CurCost);
        result.remove(CurCost);
        Cost first = new Cost(CurCost);
        Cost third = new Cost(CurCost);
        third.setBegin(inCost.getEnd());
        result.add(index, third);
        result.add(index, inCost);
        first.setEnd(inCost.getBegin());
        result.add(index, first);
    }

    /**
     * Метод добавляет в коллекцию новую цену если она перекрывает цену во второй части. И изменяет существующую
     */
    private static void last(List<Cost> result, Cost inCost , Cost CurCost) {
        if (CurCost.getValue() != inCost.getValue()) {
            result.add(inCost);
            CurCost.setBegin(inCost.getEnd());
        } else {
            result.remove(CurCost);
            inCost.setEnd(CurCost.getEnd());
            result.add(inCost);
        }
    }

    /**
     * Метод изменяет цены если они перекрываются в передней части и не заканчивается.
     */
    private static void ahead(List<Cost> result, Cost inCost , Cost CurCost) {
        if (CurCost.getValue() != inCost.getValue()) {
            CurCost.setEnd(inCost.getBegin());
        } else {
            inCost.setBegin(CurCost.getBegin());
            result.remove(CurCost);
        }
    }
}
