import java.util.HashMap;
import java.util.Map;

public enum NodeType {
  ELEMENT_NODE              (1,"ELEMENT"),
  ATTRIBUTE_NODE            (2,"ATTRIBUTE"),
  TEXT_NODE                 (3,"TEXT"),
  CDATA_SECTION_NODE        (4,"CDATA_SECTION"),
  ENTITY_REFERENCE_NODE     (5,"ENTITY_REFERENCE"),
  ENTITY_NODE               (6,"ENTITY"),
  PROCESSING_INSTRUCTION_NODE (7,"PROCESSING_INSTRUCTION"),
  COMMENT_NODE              (8,"COMMENT"),
  DOCUMENT_NODE             (9,"DOCUMENT"),
  DOCUMENT_TYPE_NODE        (10,"DOCUMENT_TYPE"),
  DOCUMENT_FRAGMENT_NODE    (11,"DOCUMENT_FRAGMENT"),
  NOTATION_NODE             (12,"NOTATION");

  private int id;
  private String name; 
  private static final Map<String, NodeType> _NodeTypeMap;
  
  static {
    _NodeTypeMap = new HashMap<>();
    for (NodeType v : values()) {
      _NodeTypeMap.put(v.getName(), v);
      _NodeTypeMap.put(String.valueOf(v.id), v);
    }
  }

  
  private NodeType(int id, String name) {
     this.id=id;
    this.name=name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static NodeType valueOfName(String type) {
    return _NodeTypeMap.get(type);
  }

  public static NodeType valueOfInt(int type) {
    return _NodeTypeMap.get(String.valueOf(type));
  }
}
