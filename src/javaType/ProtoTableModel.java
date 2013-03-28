package javaType;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.GeneratedMessage;


@SuppressWarnings("serial")
public class ProtoTableModel <K extends GeneratedMessage> extends AbstractTableModel{
  private List<K> _datas;
  private FieldDescriptor[] _keys;
  private Set<Integer> _dateColumn;  
  
  public ProtoTableModel(List<K> datas, Set<Integer> dateColumns) {
    _datas = datas;
    _dateColumn = dateColumns;
    K data = _datas.get(0);
    Set<FieldDescriptor> keySet = data.getAllFields().keySet();
    FieldDescriptor[] keys = new FieldDescriptor[keySet.size()];
    int i = 0;
    for(FieldDescriptor des : keySet) keys[i++] = des;
        
    _keys = keys;
  }

  @Override
  public int getRowCount() {
    return _datas.size();
  }

  @Override
  public int getColumnCount() {
    return _keys.length;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Object value = _datas.get(rowIndex).getField(_keys[columnIndex]);
    if(_dateColumn.contains(columnIndex)) {
      return new Date((Long) value);
    }
    return value;
  }
  
  @Override
  public String getColumnName(int columnIdx) {
    String name = _keys[columnIdx].getName();
    return name;
  }
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public Class getColumnClass(int c) {
    return getValueAt(0, c).getClass();
  }
  
}
