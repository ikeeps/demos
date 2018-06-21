package javaType;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;

public class ProtoBufUtil {
  public static StringBuilder toJSON(GeneratedMessage message) {
    StringBuilder builder = new StringBuilder();
    int last = message.getDescriptorForType().getFields().size();
    int idx = 0;
    builder.append('{');
    for (FieldDescriptor desc : message.getDescriptorForType().getFields()) {
      /*
      if (desc.isOptional() && !message.hasField(desc)) {
        ++idx;
        continue;
      }
      */
      
      Object obj = message.getField(desc);
      builder.append('\"').append(desc.getName()).append('\"').append(":");
      if (desc.isRepeated()) {
        builder.append('[');
        int repeatedFieldCount = message.getRepeatedFieldCount(desc);
        for (int i = 0; i < repeatedFieldCount; ++i) {
          Object repeatedField = message.getRepeatedField(desc, i);
          append(builder, repeatedField, desc);
          if (i < repeatedFieldCount - 1) {
            builder.append(',');
          }
        }
        builder.append(']');
      } else {
        append(builder, obj, desc);
      }
      
      if (++idx != last) {
        builder.append(',');
      }
    }
    builder.append('}');
    return builder;
  }
  
  private static void append(StringBuilder builder, Object obj, FieldDescriptor desc) {
    if (obj instanceof GeneratedMessage) {
        StringBuilder subBuilder = toJSON((GeneratedMessage) obj);
        builder.append(subBuilder);
      } else if (desc.getJavaType() == JavaType.ENUM) {
        builder.append(((EnumValueDescriptor) obj).getNumber());
      } else if (desc.getJavaType() == JavaType.STRING ||
          desc.getType() == FieldDescriptor.Type.INT64 ||
          desc.getType() == FieldDescriptor.Type.UINT64) {
        builder.append('\"');
        builder.append(obj.toString());
        builder.append('\"');
      } else {
        builder.append(obj.toString());
      }
  }
    
  public static Message create(GeneratedMessage message, JSONObject value) throws JSONException {
    Builder builder = message.newBuilderForType();
    for (FieldDescriptor desc : message.getDescriptorForType().getFields()) {
      if (desc.isOptional() && !value.has(desc.getName())) {
        continue;
      }
      /*
      if (desc.isRepeated()) {
        JSONArray jsonArray = value.getJSONArray(desc.getName());
        if (desc.getType() == FieldDescriptor.Type.MESSAGE) {
          GeneratedMessage def = name2MessageObj.get(desc.getFullName());
          for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Message create = create(def, jsonObject);
            builder.addRepeatedField(desc, create);
          }
        } else {
          for (int i = 0; i < jsonArray.length(); ++i) {
            Object jsonObject = jsonArray.get(i);
            builder.addRepeatedField(desc, jsonObject);
          }
        }
      } else {
        builder.setField(desc, value.get(desc.getName()));
      }
      */

      if (desc.getType() == FieldDescriptor.Type.MESSAGE) {
        GeneratedMessage def = name2MessageObj.get(desc.getFullName());        
        if (desc.isRepeated()) {
          JSONArray jsonArray = value.getJSONArray(desc.getName());
          for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Message create = create(def, jsonObject);
            builder.addRepeatedField(desc, create);
          }
        } else {
          Message create = create(def, value.getJSONObject(desc.getName()));
          builder.setField(desc, create);
        }
      } else if (desc.getType() == FieldDescriptor.Type.ENUM){
        int enumvalue = value.getInt(desc.getName());
        EnumValueDescriptor findValueByNumber = desc.getEnumType().findValueByNumber(enumvalue);
        builder.setField(desc, findValueByNumber);
      } else if (desc.getType() == FieldDescriptor.Type.INT64 ||
          desc.getType() == FieldDescriptor.Type.UINT64) {
        builder.setField(desc, Long.parseLong(value.getString(desc.getName())));
      } else {
        builder.setField(desc, value.get(desc.getName()));
      }
    }
    return builder.build();
  }
  
  
  
  
  private static Map<String, GeneratedMessage> name2MessageObj = new HashMap<String, GeneratedMessage>();
  static {
    /*
    name2MessageObj.put(NamesCollection.getDescriptor().findFieldByName("names").getFullName()
        , Names.getDefaultInstance());
    name2MessageObj.put(GiftMails.getDescriptor().findFieldByName("names").getFullName()
        , Names.getDefaultInstance());
    name2MessageObj.put(GiftMails.getDescriptor().findFieldByName("gitfs").getFullName()
        , Gift.getDefaultInstance());
    name2MessageObj.put(EquipAttributes.getDescriptor().findFieldByName("attributes").getFullName()
        , Attribute.getDefaultInstance());
    name2MessageObj.put(EquipAttributes.getDescriptor().findFieldByName("owner").getFullName()
        , Names.getDefaultInstance());
    name2MessageObj.put(ItemUID.getDescriptor().findFieldByName("owner").getFullName()
        , Names.getDefaultInstance());
    name2MessageObj.put(AccountCollection.getDescriptor().findFieldByName("accounts").getFullName()
        , Account.getDefaultInstance());
    name2MessageObj.put(Account.getDescriptor().findFieldByName("names").getFullName()
        , Names.getDefaultInstance());
        */
  }
  
  public static void main(String[] args) throws JSONException {
    String for_names = "{playerName:'asdf',accountName:'adsf'}";
    // Names ins_for_names = Names.getDefaultInstance();
  }
}
