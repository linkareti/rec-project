package testCorbaURL;


/**
* testCorbaURL/ManagerPOATie.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from I:/Projects/REC/IdlCompile/Simple.idl
* Sexta-feira, 9 de Janeiro de 2004 15H11m GMT
*/

public class ManagerPOATie extends ManagerPOA
{

  // Constructors

  public ManagerPOATie ( testCorbaURL.ManagerOperations delegate ) {
      this._impl = delegate;
  }
  public ManagerPOATie ( testCorbaURL.ManagerOperations delegate , org.omg.PortableServer.POA poa ) {
      this._impl = delegate;
      this._poa      = poa;
  }
  public testCorbaURL.ManagerOperations _delegate() {
      return this._impl;
  }
  public void _delegate (testCorbaURL.ManagerOperations delegate ) {
      this._impl = delegate;
  }
  public org.omg.PortableServer.POA _default_POA() {
      if(_poa != null) {
          return _poa;
      }
      else {
          return super._default_POA();
      }
  }
  public void sayHello (String nome)
  {
    _impl.sayHello(nome);
  } // sayHello

  private testCorbaURL.ManagerOperations _impl;
  private org.omg.PortableServer.POA _poa;

} // class ManagerPOATie