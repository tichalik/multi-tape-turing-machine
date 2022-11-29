package pl.polsl.tomasz.michalik.view;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import pl.polsl.tomasz.michalik.model.Tape;

/**
 * Table representing tapes of the turing machine
 *
 * @author Tomasz Michalik
 * @version 1.0
 */
class TapeTableModel extends AbstractTableModel {

    public TapeTableModel() {
        tapes = new ArrayList<>();
        data = new ArrayList<>();

        headers = new ArrayList<>();
        Integer halfColumns = numberOfColumns / 2;
        for (Integer i = 0; i < numberOfColumns; i++) {
            Integer result = i - halfColumns;
            headers.add(result.toString());
        }
        headers.set(halfColumns, "V");

    }

    private ArrayList<Tape> tapes;
    private String blank;
    ArrayList<ArrayList<String>> data;
    private final int numberOfColumns = 9;
    private ArrayList<String> headers;

    private void update() {
        final int halfColumns = numberOfColumns / 2;
        data = new ArrayList<>();
        for (Tape t : tapes) {
            ArrayList<String> row = new ArrayList<>(numberOfColumns);
            for (int i = 0; i < numberOfColumns; i++) {
                row.add(new String());
            }
            ArrayList<String> contents = t.getContents();

            //setting the columns to the left of current position
            {
                int cPos = t.getPosition();
                int rPos = halfColumns;
                while (cPos >= 0 && rPos >= 0) {
                    row.set(rPos, contents.get(cPos));
                    rPos--;
                    cPos--;
                }
                while (rPos >= 0) {
                    row.set(rPos, blank);
                    rPos--;
                }
            }

            //setting the columns to the right of current position
            {
                int cPos = t.getPosition();
                int rPos = halfColumns;
                while (cPos < contents.size() && rPos < numberOfColumns) {
                    row.set(rPos, contents.get(cPos));
                    rPos++;
                    cPos++;
                }
                while (rPos < numberOfColumns) {
                    row.set(rPos, blank);
                    rPos++;
                }
            }
            data.add(row);

        }
        fireTableDataChanged();
    }

    /**
     * responsible for showing the column headers
     *
     * @param index index of the column
     * @return name of the column
     */
    @Override
    public String getColumnName(int index) {
        return headers.get(index);
    }

    /**
     * change occured on the external tapes and it needs to be transmited into
     * the model
     *
     * @param newTapes new tapes the table should show
     */
    public void updateTapes(ArrayList<Tape> newTapes) {
        tapes = newTapes;
        update();
    }

    /**
     * the exteernal blank symbol was changed and so the internals also must be
     * updated
     *
     * @param newBlank new blank symbol
     */
    public void updateBlank(String newBlank) {
        blank = newBlank;
        update();
    }

    /**
     * how many ross does the table have
     *
     * @return number of rows in the table
     */
    @Override
    public int getRowCount() {
        return tapes.size();
    }

    /**
     * how many columns does the table have
     *
     * @return number of columns in the table
     */
    @Override
    public int getColumnCount() {
        return numberOfColumns;
    }

    /**
     * access to table cells
     *
     * @param rowIndex picking a row
     * @param columnIndex picking a column
     * @return the value stored in the table
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

}
