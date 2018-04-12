package com.verilang.psi.factory;

import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;
import com.verilang.VerilogLanguage;
import com.verilang.VerilogLexer;
import org.antlr.jetbrains.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.jetbrains.adaptor.lexer.TokenIElementType;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiLeafNode;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class VerilogPsiLeafNodeFactory {
    private static Language LANGUAGE = VerilogLanguage.INSTANCE;

    private static Map<IElementType, Class<? extends ANTLRPsiLeafNode>> tokenIElementTypeClassMap =
            new HashMap<>();

    static {
        tokenIElementTypeClassMap.put(
                getTokenIElementType(VerilogLexer.Simple_identifier),
                SimpleIdentifierPsiLeafNode.class
        );
    }

    public static TokenIElementType getTokenIElementType(int tokenIndex) {
        return PSIElementTypeFactory.getTokenIElementTypes(LANGUAGE)
                .get(tokenIndex);
    }

    public static ANTLRPsiLeafNode createLeaf(@NotNull IElementType type, @NotNull CharSequence text) {
        if (tokenIElementTypeClassMap.containsKey(type)) {
            try {
                return tokenIElementTypeClassMap.get(type)
                        .getConstructor(IElementType.class, CharSequence.class)
                        .newInstance(type, text);
            } catch (ReflectiveOperationException ignored) {}
        }
        return new ANTLRPsiLeafNode(type, text);
    }

}
