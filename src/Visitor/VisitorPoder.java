package Visitor;

import Dominio.*;

public interface VisitorPoder {
	void visit(Pokemon p);
	void visit(Item i);
	void visit(Supporter s);
	void visit(Energy e);

}
