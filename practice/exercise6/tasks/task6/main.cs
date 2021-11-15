using System;
using System.Xml;
using System.Threading;

class Program {

  public static void startDocument() {
    Console.WriteLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
  }

  public static void Main (string[] args) {
    XmlTextWriter xw = new XmlTextWriter(Console.Out);
    xw.Formatting = Formatting.Indented;

    using (XmlReader xr = XmlReader.Create(@"rss.xml")) {
      Program.startDocument();
      
      while(xr.Read()) {
        switch(xr.NodeType) {
          case XmlNodeType.Element: {
            xw.WriteStartElement(xr.Name);
            if(xr.HasAttributes) xw.WriteAttributes(xr, false);
            if(xr.IsEmptyElement) xw.WriteEndElement();
            break;
          }
          case XmlNodeType.EndElement: {
            xw.WriteEndElement();
            break;
          }
          case XmlNodeType.Text: {
            xw.WriteValue(xr.Value.ToUpper());
            break;
          }
        }
      }
    }
  }
}
