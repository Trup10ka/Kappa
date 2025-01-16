package com.trup10ka.kappa.file.exp;

import com.trup10ka.kappa.data.export.MostOrderedProduct;

import java.util.List;

public interface ExportHandler
{
    boolean exportMostOrderedItems(List<MostOrderedProduct> mostOrderedProducts);
}
