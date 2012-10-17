package com.jxls.plus.command;

import com.jxls.plus.area.Area;
import com.jxls.plus.area.XlsArea;
import com.jxls.plus.common.CellRef;
import com.jxls.plus.common.Context;
import com.jxls.plus.common.Size;
import com.jxls.plus.util.Util;

/**
 * Implements if-else logic
 * Date: Sep 11, 2009
 * @author Leonid Vysochyn
 */
public class IfCommand extends AbstractCommand {

    String condition;
    Area ifArea;
    Area elseArea;

    public IfCommand() {
    }

    /**
     * @param condition JEXL expression for boolean condition to evaluate
     */
    public IfCommand(String condition) {
        this.condition = condition;
    }

    public IfCommand(String condition, Area ifArea, Area elseArea){
        this.condition = condition;
        this.ifArea = ifArea != null ? ifArea : XlsArea.EMPTY_AREA;
        this.elseArea = elseArea != null ? elseArea : XlsArea.EMPTY_AREA;
        super.addArea(this.ifArea);
        super.addArea(this.elseArea);
    }

    public IfCommand(String condition, XlsArea ifArea) {
        this(condition, ifArea, XlsArea.EMPTY_AREA);
    }

    public String getName() {
        return "if";
    }

    /**
     * Gets test condition as JEXL expression string
     * @return test condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Sets test condition as JEXL expression string
     * @param condition condition to test
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * Gets an area to render when the condition is evaluated to 'true'
     * @return
     */
    public Area getIfArea() {
        return ifArea;
    }

    /**
     * Gets an area to render when the condition is evaluated to 'false'
     * @return
     */
    public Area getElseArea() {
        return elseArea;
    }

    @Override
    public Command addArea(Area area) {
        if( areaList.size() >= 2 ){
            throw new IllegalArgumentException("Cannot add any more areas to this IfCommand. You can add only 1 area for 'if' part and 1 area for 'else' part");
        }
        if(areaList.isEmpty()){
            ifArea = area;
        }else {
            elseArea = area;
        }
        return super.addArea(area);
    }

    public Size applyAt(CellRef cellRef, Context context) {
        Boolean conditionResult = Util.isConditionTrue(condition, context);
        if( conditionResult ){
            return ifArea.applyAt(cellRef, context);
        }else{
            return elseArea.applyAt(cellRef, context);
        }
    }

}