package am.jsl.personalfinances.dao.transaction.mapper;

import am.jsl.personalfinances.dao.DBUtils;
import am.jsl.personalfinances.dto.transaction.TransactionByCategoryDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new TransactionByCategoryDTO instance.
 * @author hamlet
 */
public class TransactionByCategoryDTOMapper implements RowMapper<TransactionByCategoryDTO> {
    @Override
    public TransactionByCategoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransactionByCategoryDTO dto = new TransactionByCategoryDTO();

        dto.setCategory(rs.getString(DBUtils.category));
        dto.setCategoryIcon(rs.getString(DBUtils.category_icon));
        dto.setCategoryColor(rs.getString(DBUtils.category_color));

        dto.setParentCategory(rs.getString(DBUtils.parent_category));
        dto.setParentCategoryIcon(rs.getString(DBUtils.parent_category_icon));
        dto.setParentCategoryColor(rs.getString(DBUtils.parent_category_color));

        dto.setTotal(rs.getDouble(DBUtils.total));
        dto.setSymbol(rs.getString(DBUtils.symbol));
        return dto;
    }
}
