package com.verilang.psi;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;
import com.verilang.VerilogLanguage;
import com.verilang.VerilogParser;
import org.antlr.jetbrains.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.jetbrains.adaptor.lexer.RuleIElementType;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;

import java.util.HashMap;
import java.util.Map;

public class VerilogPsiElementFactory {

    private static Language LANGUAGE = VerilogLanguage.INSTANCE;

    private static Map<IElementType, Class<? extends ANTLRPsiNode>> ruleIElementTypeClassMap =
            new HashMap<>();

    static {
        ruleIElementTypeClassMap.put(
                getRuleIElementType(VerilogParser.RULE_identifier),
                IdentifierPsiNode.class
        );
        ruleIElementTypeClassMap.put(
                getRuleIElementType(VerilogParser.RULE_module_or_generate_item_declaration),
                ModuleOrGenerateItemDeclarationPsiNode.class
        );
        ruleIElementTypeClassMap.put(
                getRuleIElementType(VerilogParser.RULE_port_declaration),
                PortDeclarationPsiNode.class
        );
        ruleIElementTypeClassMap.put(
                getRuleIElementType(VerilogParser.RULE_parameter_declaration),
                ParameterDeclarationPsiNode.class
        );
        ruleIElementTypeClassMap.put(
                getRuleIElementType(VerilogParser.RULE_hierarchical_identifier),
                HierarchicalIdentifierPsiNode.class
        );
        ruleIElementTypeClassMap.put(
                getRuleIElementType(VerilogParser.RULE_list_of_port_declarations),
                ListOfPortDeclarationsPsiNode.class
        );
    }

    private static RuleIElementType getRuleIElementType(int ruleIndex) {
        return PSIElementTypeFactory.getRuleIElementTypes(LANGUAGE)
                .get(ruleIndex);
    }

    public static ANTLRPsiNode createComposite(ASTNode astNode) {
        if (ruleIElementTypeClassMap.containsKey(astNode.getElementType())) {
            try {
                return ruleIElementTypeClassMap.get(astNode.getElementType())
                        .getConstructor(ASTNode.class).newInstance(astNode);
            } catch (ReflectiveOperationException ignored) {}
        }
        return new ANTLRPsiNode(astNode);
    }
}
