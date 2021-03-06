-- Date Created : Tuesday, March 10, 2009 11:30:20
-- Target DBMS : Oracle 11g
--

-- 
-- TABLE: ALARM.ALARM_TREE 
--

CREATE TABLE ALARM.ALARM_TREE(
    COMPONENT_ID       NUMBER(38, 0)    NOT NULL,
    PARENT_CMPNT_ID    NUMBER(38, 0),
    NAME               VARCHAR2(80)     NOT NULL,
    CONFIG_TIME        TIMESTAMP(6),
    CONSTRAINT PK_ALARM_TREE PRIMARY KEY (COMPONENT_ID)
)
;



COMMENT ON COLUMN ALARM.ALARM_TREE.COMPONENT_ID IS 'Component Identifier: The id for identification of each component.'
;
COMMENT ON COLUMN ALARM.ALARM_TREE.PARENT_CMPNT_ID IS 'Parent Component Identifier:The parent id of the component in the configuration hierarchy, null for root of hierarchy.'
;
COMMENT ON COLUMN ALARM.ALARM_TREE.NAME IS 'Name: Component name.'
;
COMMENT ON COLUMN ALARM.ALARM_TREE.CONFIG_TIME IS 'Configuration Time: Time of last configuration update. '
;
-- 
-- TABLE: ALARM.COMMAND 
--

CREATE TABLE ALARM.COMMAND(
    COMPONENT_ID     NUMBER(38, 0)     NOT NULL,
    TITLE            VARCHAR2(100)     NOT NULL,
    COMMAND_ORDER    NUMBER(38, 0)     NOT NULL,
    DETAIL           VARCHAR2(4000)    NOT NULL,
    CONSTRAINT PK_COMMAND PRIMARY KEY (COMPONENT_ID, TITLE)
)
;



COMMENT ON COLUMN ALARM.COMMAND.COMPONENT_ID IS 'Component Identifier: The id for identification of each component.'
;
COMMENT ON COLUMN ALARM.COMMAND.TITLE IS 'Title: Brief description of the guidance, which will be displayed in the context menu.'
;
COMMENT ON COLUMN ALARM.COMMAND.COMMAND_ORDER IS 'Order: The order by which the commands are arranged.'
;
COMMENT ON COLUMN ALARM.COMMAND.DETAIL IS 'Detail: The related command which will be executed when you click on its title.'
;
-- 
-- TABLE: ALARM.DISPLAY 
--

CREATE TABLE ALARM.DISPLAY(
    COMPONENT_ID     NUMBER(38, 0)     NOT NULL,
    TITLE            VARCHAR2(100)     NOT NULL,
    DISPLAY_ORDER    NUMBER(38, 0)     NOT NULL,
    DETAIL           VARCHAR2(4000)    NOT NULL,
    CONSTRAINT PK_DISPLAY PRIMARY KEY (COMPONENT_ID, TITLE)
)
;



COMMENT ON COLUMN ALARM.DISPLAY.COMPONENT_ID IS 'Component Identifier: The id for identification of each component.'
;
COMMENT ON COLUMN ALARM.DISPLAY.TITLE IS 'Title: Brief description of the guidance, which will be displayed in the context menu.'
;
COMMENT ON COLUMN ALARM.DISPLAY.DISPLAY_ORDER IS 'Order: The order by which the displays are arranged.'
;
COMMENT ON COLUMN ALARM.DISPLAY.DETAIL IS 'Detail: The related display which will be launched when you click on its title.'
;
-- 
-- TABLE: ALARM.GUIDANCE 
--

CREATE TABLE ALARM.GUIDANCE(
    COMPONENT_ID      NUMBER(38, 0)     NOT NULL,
    TITLE             VARCHAR2(100)     NOT NULL,
    GUIDANCE_ORDER    NUMBER(38, 0)     NOT NULL,
    DETAIL            VARCHAR2(4000)    NOT NULL,
    CONSTRAINT PK_GUIDANCE PRIMARY KEY (COMPONENT_ID, TITLE)
)
;



COMMENT ON COLUMN ALARM.GUIDANCE.COMPONENT_ID IS 'Component Identifier: The id for identification of each component.'
;
COMMENT ON COLUMN ALARM.GUIDANCE.TITLE IS 'Title: Brief description of the guidance, which will be displayed in the context menu.'
;
COMMENT ON COLUMN ALARM.GUIDANCE.GUIDANCE_ORDER IS 'Order: The order by which the guidance are arranged.'
;
COMMENT ON COLUMN ALARM.GUIDANCE.DETAIL IS 'Detail: Guidance information which is displayed in the guidance dialog.'
;
-- 
-- TABLE: ALARM.AUTOMATED_ACTION
--

CREATE TABLE ALARM.AUTOMATED_ACTION(
    COMPONENT_ID      NUMBER(38, 0)     NOT NULL,
    TITLE             VARCHAR2(100)     NOT NULL,
    AUTO_ACTION_ORDER NUMBER(38, 0)     NOT NULL,
    DETAIL            VARCHAR2(4000)    NOT NULL,
	DELAY             NUMBER(38, 0)     NOT NULL,
	CONSTRAINT PK_AUTO_ACTION PRIMARY KEY (COMPONENT_ID, TITLE)
);



COMMENT ON COLUMN ALARM.AUTOMATED_ACTION.COMPONENT_ID IS 'Component Identifier: The id for identification of each component.'
;
COMMENT ON COLUMN ALARM.AUTOMATED_ACTION.TITLE IS 'Title: The action title.'
;
COMMENT ON COLUMN ALARM.AUTOMATED_ACTION.AUTO_ACTION_ORDER IS 'Order: The order by which the actions are arranged.'
;
COMMENT ON COLUMN ALARM.AUTOMATED_ACTION.DETAIL IS 'Detail: The action value (send email, phone, etc.).'
;
COMMENT ON COLUMN ALARM.AUTOMATED_ACTION.DELAY IS 'Delay: The action delay in seconds.'
;
-- 
-- TABLE: ALARM.PV 
--

CREATE TABLE ALARM.PV(
    COMPONENT_ID       NUMBER(38, 0)     NOT NULL,
    DESCR              VARCHAR2(100),
    ENABLED_IND        NUMBER(1, 0)      DEFAULT 0 NOT NULL,
    ANNUNCIATE_IND     NUMBER(1, 0)      DEFAULT 0 NOT NULL,
    LATCH_IND          NUMBER(1, 0)      DEFAULT 0 NOT NULL,
    DELAY              NUMBER(38, 0),
    FILTER             VARCHAR2(4000),
    DELAY_COUNT        NUMBER(38, 0),
    STATUS_ID          NUMBER(38, 0),
    SEVERITY_ID        NUMBER(38, 0),
    CUR_STATUS_ID      NUMBER(38, 0),
    CUR_SEVERITY_ID    NUMBER(38, 0),
    PV_VALUE           VARCHAR2(100),
    ALARM_TIME         TIMESTAMP(6)      DEFAULT sysdate,
    ACT_GLOBAL_ALARM_IND  NUMBER(1, 0)   DEFAULT 0 NOT NULL,
    CONSTRAINT PK_PV PRIMARY KEY (COMPONENT_ID)
);



COMMENT ON COLUMN ALARM.PV.COMPONENT_ID IS 'Component Identifier: The id for identification of each component.'
;
COMMENT ON COLUMN ALARM.PV.DESCR IS 'Description: Description that might be more meaningful than PV name.'
;
COMMENT ON COLUMN ALARM.PV.ENABLED_IND IS 'Enabled Indicator: Indicates if alarms are enabled for a given PV.'
;
COMMENT ON COLUMN ALARM.PV.ANNUNCIATE_IND IS 'Annunciate Indicator:  Indicates if alarm should be annunciated.'
;
COMMENT ON COLUMN ALARM.PV.LATCH_IND IS 'Latch Indicator: Indicates that alarm should be latched for acknowledgement, even if PV recovers.'
;
COMMENT ON COLUMN ALARM.PV.DELAY IS 'Delay: Minimum time in seconds before raising the alarm.'
;
COMMENT ON COLUMN ALARM.PV.FILTER IS 'Filter: Filter expression, may be used to compute \''enabled\'' from expression.'
;
COMMENT ON COLUMN ALARM.PV.DELAY_COUNT IS 'Count: Alarm when PV != OK more often than this count within delay.'
;
COMMENT ON COLUMN ALARM.PV.STATUS_ID IS 'Status Identifier: Alarm system state for the severity identifier.'
;
COMMENT ON COLUMN ALARM.PV.SEVERITY_ID IS 'Severity Identifier: Alarm system severity.'
;
COMMENT ON COLUMN ALARM.PV.CUR_SEVERITY_ID IS 'Current Severity Identifier: Current severity of PV.'
;
COMMENT ON COLUMN ALARM.PV.PV_VALUE IS 'Process Variable Value: PV value that caused severity/status.'
;
COMMENT ON COLUMN ALARM.PV.ALARM_TIME IS 'Alarm Time: The time of the most recent alarm.'
;
COMMENT ON COLUMN ALARM.PV.ACT_GLOBAL_ALARM_IND IS 'Indicates if PV has an active global alarm.'
;
-- 
-- TABLE: ALARM.SEVERITY 
--

CREATE TABLE ALARM.SEVERITY(
    SEVERITY_ID    NUMBER(38, 0)    NOT NULL,
    NAME           VARCHAR2(100)    NOT NULL,
    CONSTRAINT PK_SEVERITY PRIMARY KEY (SEVERITY_ID)
)
;



COMMENT ON COLUMN ALARM.SEVERITY.SEVERITY_ID IS 'Severity Identifier: Unique identifer for the alarm severity.'
;
COMMENT ON COLUMN ALARM.SEVERITY.NAME IS 'Severity Name: '
;
-- 
-- TABLE: ALARM.STATUS 
--

CREATE TABLE ALARM.STATUS(
    STATUS_ID    NUMBER(38, 0)    NOT NULL,
    NAME         VARCHAR2(100)    NOT NULL,
    CONSTRAINT PK_STATUS PRIMARY KEY (STATUS_ID)
)
;



COMMENT ON COLUMN ALARM.STATUS.STATUS_ID IS 'Status Identifier: Unique identifier for the alarm status.'
;
COMMENT ON COLUMN ALARM.STATUS.NAME IS 'Status Name: '
;
-- 
-- INDEX: ALARM.FK_ALARM_TREE_TO_ALARM_TREE 
--

CREATE INDEX ALARM.FK_ALARM_TREE_TO_ALARM_TREE ON ALARM.ALARM_TREE(PARENT_CMPNT_ID)
;
-- 
-- INDEX: ALARM.FK_COMMAND_TO_ALARM_TREE 
--

CREATE INDEX ALARM.FK_COMMAND_TO_ALARM_TREE ON ALARM.COMMAND(COMPONENT_ID)
;
-- 
-- INDEX: ALARM.FK_DISPLAY_TO_ALARM_TREE 
--

CREATE INDEX ALARM.FK_DISPLAY_TO_ALARM_TREE ON ALARM.DISPLAY(COMPONENT_ID)
;
-- 
-- INDEX: ALARM.FK_GUIDANCE_TO_ALARM_TREE 
--

CREATE INDEX ALARM.FK_GUIDANCE_TO_ALARM_TREE ON ALARM.GUIDANCE(COMPONENT_ID)
;
-- 
-- INDEX: FK_AUTO_ACTION_TO_ALARM_TREE 
--

CREATE INDEX ALARM.FK_AUTO_ACTION_TO_ALARM_TREE ON ALARM.AUTOMATED_ACTION(COMPONENT_ID)
;
-- 
-- INDEX: ALARM.FK_PV_TO_STATUS 
--

CREATE INDEX ALARM.FK_PV_TO_STATUS ON ALARM.PV(STATUS_ID)
;
-- 
-- INDEX: ALARM.FK_PV_TO_SEVERITY 
--

CREATE INDEX ALARM.FK_PV_TO_SEVERITY ON ALARM.PV(SEVERITY_ID)
;
-- 
-- INDEX: ALARM.FK_PV_TO_ALARM_TREE 
--

CREATE INDEX ALARM.FK_PV_TO_ALARM_TREE ON ALARM.PV(COMPONENT_ID)
;
-- 
-- TABLE: ALARM.ALARM_TREE 
--

ALTER TABLE ALARM.ALARM_TREE ADD CONSTRAINT FK_ALARM_TREE_TO_ALARM_TREE 
    FOREIGN KEY (PARENT_CMPNT_ID)
    REFERENCES ALARM.ALARM_TREE(COMPONENT_ID)
;


-- 
-- TABLE: ALARM.COMMAND 
--

ALTER TABLE ALARM.COMMAND ADD CONSTRAINT FK_COMMAND_TO_ALARM_TREE 
    FOREIGN KEY (COMPONENT_ID)
    REFERENCES ALARM.ALARM_TREE(COMPONENT_ID)
;


-- 
-- TABLE: ALARM.DISPLAY 
--

ALTER TABLE ALARM.DISPLAY ADD CONSTRAINT FK_DISPLAY_TO_ALARM_TREE 
    FOREIGN KEY (COMPONENT_ID)
    REFERENCES ALARM.ALARM_TREE(COMPONENT_ID)
;


-- 
-- TABLE: ALARM.GUIDANCE 
--

ALTER TABLE ALARM.GUIDANCE ADD CONSTRAINT FK_GUIDANCE_TO_ALARM_TREE 
    FOREIGN KEY (COMPONENT_ID)
    REFERENCES ALARM.ALARM_TREE(COMPONENT_ID)
;


-- 
-- TABLE: ALARM.AUTOMATED_ACTION 
--

ALTER TABLE ALARM.AUTOMATED_ACTION ADD CONSTRAINT FK_AUTO_ACTION_TO_ALARM_TREE 
    FOREIGN KEY (COMPONENT_ID)
    REFERENCES ALARM.ALARM_TREE(COMPONENT_ID)
;


-- 
-- TABLE: ALARM.PV 
--

ALTER TABLE ALARM.PV ADD CONSTRAINT FK_CUR_SVRTY_TO_SEVERITY 
    FOREIGN KEY (CUR_SEVERITY_ID)
    REFERENCES ALARM.SEVERITY(SEVERITY_ID)
;

ALTER TABLE ALARM.PV ADD CONSTRAINT FK_PV_TO_ALARM_TREE 
    FOREIGN KEY (COMPONENT_ID)
    REFERENCES ALARM.ALARM_TREE(COMPONENT_ID)
;

ALTER TABLE ALARM.PV ADD CONSTRAINT FK_PV_TO_SEVERITY 
    FOREIGN KEY (SEVERITY_ID)
    REFERENCES ALARM.SEVERITY(SEVERITY_ID)
;

ALTER TABLE ALARM.PV ADD CONSTRAINT FK_PV_TO_STATUS 
    FOREIGN KEY (STATUS_ID)
    REFERENCES ALARM.STATUS(STATUS_ID)
;


