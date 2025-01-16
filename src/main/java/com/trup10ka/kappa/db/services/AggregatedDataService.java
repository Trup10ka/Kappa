package com.trup10ka.kappa.db.services;

import com.trup10ka.kappa.data.export.MostOrderedProduct;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface AggregatedDataService
{
    @Nullable
    List<MostOrderedProduct> exportMostOrderedItems();
}
